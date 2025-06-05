import React, { useState } from 'react';
import axios from 'axios';
import Logo from '../../assets/Logo_ChargeMate.png';
import { Link, useNavigate } from 'react-router-dom';

export default function SignUpEVdriver() {
    const [formData, setFormData] = useState({
        email: '',
        name: '',
        password: '',
        confirmPassword: '',
    });

    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.password !== formData.confirmPassword) {
            setError('Passwords do not match.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/register/ev-driver', {
                email: formData.email,
                name: formData.name,
                password: formData.password,
                userType: 'EV_DRIVER',
            });

            if (response.status === 201) {
                navigate('/signin');
            }
        } catch (err) {
            if (err.response?.data?.error) {
                setError(err.response.data.error);
            } else {
                setError('Registration failed. Please try again.');
            }
        }
    };

    return (
        <div className="min-h-screen bg-[#202025] text-white flex items-center justify-center p-4">
            <div className="w-full max-w-md space-y-6">
                <img className="mx-auto h-16 w-auto mb-4" src={Logo} alt="ChargeMate Logo" />

                <div className="text-center space-y-1">
                    <h2 className="text-xl font-semibold">Create an account as an EV Driver</h2>
                    <p className="text-sm text-gray-400">Enter the following details to get started</p>
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
                        name="name"
                        type="text"
                        placeholder="first and last names"
                        value={formData.name}
                        onChange={handleChange}
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                        required
                    />
                    <input
                        name="password"
                        type="password"
                        placeholder="define password"
                        value={formData.password}
                        onChange={handleChange}
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                        required
                    />
                    <input
                        name="confirmPassword"
                        type="password"
                        placeholder="confirm password"
                        value={formData.confirmPassword}
                        onChange={handleChange}
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                        required
                    />
                    <button type="submit" className="btn w-full bg-gradient-to-r from-cyan-400 to-blue-500 text-white rounded-full">
                        Continue
                    </button>
                </form>

                <div className="flex items-center gap-2 text-gray-500">
                    <hr className="flex-1 border-gray-700" />
                    <span>or</span>
                    <hr className="flex-1 border-gray-700" />
                </div>

                <Link to="/signup_stationoperator" className="block mt-3">
                    <button className="btn w-full bg-white text-gray-900 rounded-full">
                        Create an account as a Station Operator
                    </button>
                </Link>

                <Link to="/signin" className="block mt-3">
                    <button className="btn w-full bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-full">
                        Sign In
                    </button>
                </Link>

                <p className="text-xs text-center text-gray-500">
                    By clicking continue, you agree to our{' '}
                    <span className="underline">Terms of Service</span> and{' '}
                    <span className="underline">Privacy Policy</span>.
                </p>
            </div>
        </div>
    );
}
