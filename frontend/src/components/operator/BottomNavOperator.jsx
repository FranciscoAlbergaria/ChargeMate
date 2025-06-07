import React from 'react';
import { Home, BarChart2, Settings, User } from 'lucide-react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function BottomNavStationOperator() {
    const location = useLocation();
    const navigate = useNavigate();

    const items = [
        { path: '/dashboard_operator', icon: <Home size={24} />, label: 'Stations' },
        { path: '/analytics', icon: <BarChart2 size={24} />, label: 'Analytics' },
        { path: '/settings', icon: <Settings size={24} />, label: 'Settings' },
        { path: '/profile_operator', icon: <User size={24} />, label: 'Profile' },
    ];

    return (
        <nav className="fixed bottom-0 left-0 right-0 bg-[#1A1A1E] text-white py-2 border-t border-gray-800 z-50">
            <div className="flex justify-around items-center">
                {items.map(({ path, icon }, i) => (
                    <button
                        key={i}
                        onClick={() => navigate(path)}
                        className={`p-2 rounded-full ${
                            location.pathname === path ? 'text-cyan-400' : 'text-gray-400'
                        }`}
                    >
                        {icon}
                    </button>
                ))}
            </div>
        </nav>
    );
}
