import { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [userKey, setUserKey] = useState({
    username: "example",
    password: "password",
  });

  const [search, setSearch] = useState<string>("");
  const [materials, setMaterials] = useState<string[]>([]);
  const [recyclingCenters, setRecyclingCenters] = useState([]);
  const [loggedIn, setLoggedIn] = useState<boolean>(false);
  const [location, setLocation] = useState<{ lat: number; lng: number } | null>(
    null
  );

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setLocation({
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        });
      });
    }
  }, []);

  useEffect(() => {
    fetch("http://localhost:8082/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userKey),
      credentials: "include",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error(error);
      })
      .then(() => {
        setLoggedIn(true);
      });
  }, [userKey]);

  useEffect(() => {
    if (loggedIn == true) {
      let url = `http://localhost:8082/api/recycling-centers/all`;
      if (search.length != 0) {
        url += `?search=${search}`;
      } else if (location != null) {
        url += `?latlng=${location.lat},${location.lng}`;
      }

      console.log(url);

      fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          setRecyclingCenters(data._embedded.recycling_center_list);
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      setRecyclingCenters([]);
    }
  }, [loggedIn, location, search]);

  useEffect(() => {
    fetch(
      `http://localhost:8082/api/recycling-centers/all?materials=${materials}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setRecyclingCenters(data._embedded.recycling_center_list);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [materials]);

  return (
    <div>
      <form
        onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
          e.preventDefault();
          setUserKey({
            username: (e.target as HTMLFormElement).username.value,
            password: (e.target as HTMLFormElement).password.value,
          });
        }}
        method="post"
      >
        <input type="text" name="username" placeholder="Username" />
        <input type="password" name="password" placeholder="Password" />
        <button type="submit">Login</button>
        <button
          type="button"
          onClick={() => {
            fetch("http://localhost:8082/api/auth/logout", {
              method: "DELETE",
              headers: {
                "Content-Type": "application/json",
              },
              credentials: "include",
            })
              .then((response) => response.json())
              .then((data) => {
                console.log(data);
              })
              .catch((error) => {
                console.error(error);
              });
            setLoggedIn(false);
          }}
        >
          Logout
        </button>
      </form>
      <input
        type="text"
        value={search}
        placeholder="Search..."
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          setSearch(e.target.value)
        }
      />
      <input
        type="text"
        value={materials}
        placeholder="Filter materials..."
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          setMaterials(
            e.target.value.split(",").map((material) => material.toUpperCase())
          )
        }
      />
      {loggedIn ? (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>Location</th>
              <th>Materials</th>
              <th>Starting Time</th>
              <th>End Time</th>
            </tr>
          </thead>
          <tbody>
            {recyclingCenters.map((recyclingCenter: any) => (
              <tr key={recyclingCenter.id}>
                <td>{recyclingCenter.id}</td>
                <td>{recyclingCenter.name}</td>
                <td>
                  {recyclingCenter.location.address +
                    ", " +
                    recyclingCenter.location.city +
                    ", " +
                    recyclingCenter.location.county +
                    ", " +
                    recyclingCenter.location.zipcode +
                    ", " +
                    recyclingCenter.location.latitude +
                    ", " +
                    recyclingCenter.location.longitude}
                </td>
                <td>{recyclingCenter.materials.join(", ")}</td>
                <td>{recyclingCenter.start_time}</td>
                <td>{recyclingCenter.end_time}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (<h1>Please login</h1>)}
    </div>
  );
}

export default App;
