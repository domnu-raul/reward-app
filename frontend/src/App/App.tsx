import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'


function About() {
  return <h2>About</h2>
}

function UsersPage() {
  return <h2>Users</h2>
}
export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/about">
          <About />
        </Route>
        <Route path="/users">
          <UsersPage />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}