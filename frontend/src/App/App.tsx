import './App.css'
import {
  BrowserRouter as Router,
  Route,
  Routes
} from 'react-router-dom'
import About from '../About/About'


export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="about" element={<About />} />
        <Route path="users">
          <h2>users</h2>
        </Route>
      </Routes>
    </Router>
  )
}