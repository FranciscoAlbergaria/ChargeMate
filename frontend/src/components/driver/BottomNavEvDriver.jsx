import React from 'react';
import { Map, CalendarDays, Star, BarChart2, User } from 'lucide-react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function BottomNavEvDriver() {
    const location = useLocation();
    const navigate = useNavigate();

    const items = [
        { path: '/dashboard_evdriver', icon: <Map size={24} />, label: 'Map' },
        { path: '/booking', icon: <CalendarDays size={24} />, label: 'Calendar' },
        { path: '/favorites', icon: <Star size={24} />, label: 'Favorites' },
        { path: '/charging_history', icon: <BarChart2 size={24} />, label: 'Charging History' },
        { path: '/profile', icon: <User size={24} />, label: 'Profile' },
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
