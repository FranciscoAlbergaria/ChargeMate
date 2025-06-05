import React from 'react';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

export default function Profile() {
    // Placeholder user data – you can replace this with real data later
    const user = {
        name: 'Alex Johnson',
        email: 'alex@example.com',
        role: 'EV Driver', // or "Station Operator"
    };

    return (
        <div className="min-h-screen bg-[#202025] text-white pb-20">
            <Header />

            <div className="p-5 space-y-6">
                <h1 className="text-2xl font-bold">My Profile</h1>

                <div className="bg-gray-800 rounded-xl p-4 space-y-2 shadow">
                    <div>
                        <p className="text-gray-400 text-sm">Name</p>
                        <p className="text-lg">{user.name}</p>
                    </div>
                    <div>
                        <p className="text-gray-400 text-sm">Email</p>
                        <p className="text-lg">{user.email}</p>
                    </div>
                    <div>
                        <p className="text-gray-400 text-sm">Role</p>
                        <p className="text-lg">{user.role}</p>
                    </div>
                </div>

                {/* Optional: Buttons */}
                <div className="space-y-3">
                    <button className="btn w-full btn-outline">Edit Profile</button>
                    <button className="btn w-full bg-red-600 text-white">Log Out</button>
                </div>
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
