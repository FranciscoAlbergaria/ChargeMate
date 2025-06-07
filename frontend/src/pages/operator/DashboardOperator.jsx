import React from 'react';
import Header from '../../components/shared/Header.jsx';
import BottomNavStationOperator from "../../components/operator/BottomNavOperator.jsx";


export default function DashboardOperator() {
    return (
        <div className="min-h-screen bg-[#202025] text-white p-5">
            <Header />
            <h1 className="text-2xl font-bold mb-4">Your Stations</h1>
            <p className="text-sm text-gray-400 mb-4">Station list not yet implemented</p>

            <div className="bg-gray-800 p-4 rounded-xl">
                <p>No stations to show yet.</p>
            </div>
            <BottomNavStationOperator />
        </div>
    );
}
