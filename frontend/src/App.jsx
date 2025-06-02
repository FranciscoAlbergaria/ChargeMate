import SignUpEVdriver from "./pages/SignUpEVdriver.jsx";
import SignUpStationOperator from "./pages/SignUpStationOperator.jsx";
import SignIn from "./pages/SignIn.jsx";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

function App() {
    return (
        <>
            <Routes>
                <Route path="/signup_evdriver" element={<SignUpEVdriver />} />
                <Route path="/signup_stationoperator" element={<SignUpStationOperator />} />
                <Route path="/signin" element={<SignIn />} />
            </Routes>
        </>
    )
}

export default App;