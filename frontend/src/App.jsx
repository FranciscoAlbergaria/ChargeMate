import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

import SignUpEVdriver from "./pages/driver/SignUpEVdriver.jsx";
import SignUpStationOperator from "./pages/operator/SignUpStationOperator.jsx";
import SignIn from "./pages/SignIn.jsx";
import DashboardEVdriver from "./pages/driver/DashboardEVdriver.jsx";
import Booking from "./pages/driver/Booking.jsx";
import Favorites from "./pages/driver/Favorites.jsx";
import ChargingHistory from "./pages/driver/ChargingHistory.jsx";
import Profile from "./pages/driver/Profile.jsx";

import Analytics from "./pages/operator/Analytics.jsx";

function App() {
    return (
        <>
            <Routes>
                <Route path="/signup_evdriver" element={<SignUpEVdriver />} />
                <Route path="/signup_stationoperator" element={<SignUpStationOperator />} />
                <Route path="/signin" element={<SignIn />} />
                <Route path="/dashboard_evdriver" element={<DashboardEVdriver />} />
                <Route path="/booking" element={<Booking />} />
                <Route path="/favorites" element={<Favorites />} />
                <Route path="/charging_history" element={<ChargingHistory />} />
                <Route path="/profile" element={<Profile />} />

                <Route path="/analytics" element={<Analytics />} />
            </Routes>
        </>
    )
}

export default App;