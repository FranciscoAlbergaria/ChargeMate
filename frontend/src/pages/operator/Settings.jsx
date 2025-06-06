import React from 'react';
import Header from '../../components/shared/Header.jsx';
import BottomNavStationOperator from "../../components/operator/BottomNavOperator.jsx";


export default function Settings() {
    return (
        <div className="min-h-screen bg-[#202025] text-white p-5">
            <Header />
            <h1 className="text-2xl font-bold mb-4">Pricing Settings</h1>
            <p className="text-sm text-gray-400 mb-6">Configure off-peak rates (UI only for now)</p>

            <form className="bg-gray-800 rounded-xl p-6 space-y-4">
                <div>
                    <label className="block mb-1">Off-peak start (hour)</label>
                    <input type="time" className="input w-full bg-gray-700 text-white" />
                </div>
                <div>
                    <label className="block mb-1">Off-peak end (hour)</label>
                    <input type="time" className="input w-full bg-gray-700 text-white" />
                </div>
                <div>
                    <label className="block mb-1">Discount %</label>
                    <input type="number" min="0" max="100" className="input w-full bg-gray-700 text-white" />
                </div>
                <button type="submit" className="btn bg-green-600 hover:bg-green-700 text-white w-full rounded-full">
                    Save Settings
                </button>
            </form>
            <BottomNavStationOperator />
        </div>
    );
}
