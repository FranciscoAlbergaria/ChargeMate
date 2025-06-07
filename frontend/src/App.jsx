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

import DashboardOperator from "./pages/operator/DashboardOperator.jsx";
import Settings from "./pages/operator/Settings.jsx";
import Analytics from "./pages/operator/Analytics.jsx";
import ProfileOperator from "./pages/operator/ProfileOperator.jsx";

import PrivateRoute from "./PrivateRoute.jsx";

function App() {
    return (
        <Routes>
            {/* Public */}
            <Route path="/signup_evdriver" element={<SignUpEVdriver />} />
            <Route path="/signup_stationoperator" element={<SignUpStationOperator />} />
            <Route path="/signin" element={<SignIn />} />

            {/* EV DRIVER Routes (Protected) */}
            <Route path="/dashboard_evdriver" element={
                <PrivateRoute><DashboardEVdriver /></PrivateRoute>
            } />
            <Route path="/booking" element={
                <PrivateRoute><Booking /></PrivateRoute>
            } />
            <Route path="/favorites" element={
                <PrivateRoute><Favorites /></PrivateRoute>
            } />
            <Route path="/charging_history" element={
                <PrivateRoute><ChargingHistory /></PrivateRoute>
            } />
            <Route path="/profile" element={
                <PrivateRoute><Profile /></PrivateRoute>
            } />

            {/* STATION OPERATOR Routes (Protected) */}
            <Route path="/dashboard_operator" element={
                <PrivateRoute><DashboardOperator /></PrivateRoute>
            } />
            <Route path="/analytics" element={
                <PrivateRoute><Analytics /></PrivateRoute>
            } />
            <Route path="/settings" element={
                <PrivateRoute><Settings /></PrivateRoute>
            } />
            <Route path="/profile_operator" element={
                <PrivateRoute><ProfileOperator /></PrivateRoute>
            } />
        </Routes>
    );
}

export default App;
