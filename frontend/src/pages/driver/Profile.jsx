import React from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/shared/Header.jsx';
import BottomNavEvDriver from '../../components/driver/BottomNavEvDriver.jsx';

export default function Profile() {
    const navigate = useNavigate();

    // Dados reais do localStorage (token + user info)
    const userEmail = localStorage.getItem('email');
    const userRole = localStorage.getItem('role');
    const userName = localStorage.getItem('name');

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('email');
        localStorage.removeItem('name');
        navigate('/signin');
    };

    return (
        <div className="min-h-screen bg-[#202025] text-white pb-20">
            <Header />

            <div className="p-5 space-y-6">
                <h1 className="text-2xl font-bold">My Profile</h1>

                <div className="bg-gray-800 rounded-xl p-4 space-y-2 shadow">
                    <div>
                        <p className="text-gray-400 text-sm">Name</p>
                        <p className="text-lg">{userName || 'N/A'}</p>
                    </div>
                    <div>
                        <p className="text-gray-400 text-sm">Email</p>
                        <p className="text-lg">{userEmail || 'N/A'}</p>
                    </div>
                    <div>
                        <p className="text-gray-400 text-sm">Role</p>
                        <p className="text-lg">{userRole || 'N/A'}</p>
                    </div>
                </div>

                <div className="space-y-3">
                    <button className="btn w-full btn-outline" disabled>
                        Edit Profile
                    </button>
                    <button
                        onClick={handleLogout}
                        className="btn w-full bg-red-600 text-white hover:bg-red-700"
                    >
                        Log Out
                    </button>
                </div>
            </div>

            <BottomNavEvDriver />
        </div>
    );
}
