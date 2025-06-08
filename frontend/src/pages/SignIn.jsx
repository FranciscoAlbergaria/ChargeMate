import React, { useState } from 'react';
import axios from 'axios';
import Logo from '../assets/Logo_ChargeMate.png';
import { Link, useNavigate } from 'react-router-dom';

export default function SignIn() {
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/v1/auth/login`, formData);

            const { token, role } = response.data;
            localStorage.setItem('token', token);

            if (role === 'EV_DRIVER') {
                navigate('/dashboard_evdriver');
            } else if (role === 'STATION_OPERATOR') {
                navigate('/dashboard_operator');
            } else {
                setError('Unknown user role.');
            }
        } catch (err) {
            setError('Invalid credentials. Please try again.');
        }
    };

    return (
        <div className="min-h-screen bg-[#202025] text-white flex items-center justify-center p-4">
            <div className="w-full max-w-md space-y-6">
                <img className="mx-auto h-16 w-auto mb-4" src={Logo} alt="ChargeMate Logo" />

                <div className="text-center space-y-1">
                    <h2 className="text-xl font-semibold">Sign In</h2>
                    <p className="text-sm text-gray-400">Welcome back! Please sign in to your account.</p>
                </div>

                {error && <p className="text-red-400 text-sm text-center">{error}</p>}

                <form className="space-y-4" onSubmit={handleSubmit}>
                    <input
                        name="email"
                        type="email"
                        placeholder="email@domain.com"
                        value={formData.email}
                        onChange={handleChange}
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                        required
                    />
                    <input
                        name="password"
                        type="password"
                        placeholder="password"
                        value={formData.password}
                        onChange={handleChange}
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                        required
                    />
                    <button type="submit" className="btn w-full bg-white text-gray-900 rounded-full">
                        Sign In
                    </button>
                </form>

                <div className="flex items-center gap-2 text-gray-500">
                    <hr className="flex-1 border-gray-700" />
                    <span>or</span>
                    <hr className="flex-1 border-gray-700" />
                </div>

                <Link to="/signup_evdriver" className="block mt-2">
                    <button className="btn w-full bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-full">
                        Sign Up
                    </button>
                </Link>
            </div>
        </div>
    );
}
