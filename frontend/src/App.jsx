import SignUpEVdriver from "./pages/SignUpEVdriver.jsx";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

function App() {
    return (
        <>
            <Routes>
                <Route path="/signup_evdriver" element={<SignUpEVdriver />} />
            </Routes>
        </>
    )
}

export default App;