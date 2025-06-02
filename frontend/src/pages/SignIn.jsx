import React from 'react';
import Logo from '../assets/Logo_ChargeMate.png';
import { Link } from 'react-router-dom';

export default function SignIn() {
    return (
        <div className="min-h-screen bg-[#202025] text-white flex items-center justify-center p-4">
            <div className="w-full max-w-md space-y-6">
                {/* Logo */}
                <img className="mx-auto h-16 w-auto mb-4" src={Logo} alt="ChargeMate Logo" />

                {/* Title & Subtitle */}
                <div className="text-center space-y-1">
                    <h2 className="text-xl font-semibold">Sign In</h2>
                    <p className="text-sm text-gray-400">Welcome back! Please sign in to your account.</p>
                </div>

                {/* Form */}
                <form className="space-y-4">
                    <input
                        type="email"
                        placeholder="email@domain.com"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="password"
                        placeholder="password"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />

                    <button className="btn w-full bg-white text-gray-900 rounded-full">
                        Sign In
                    </button>
                </form>

                {/* Divider */}
                <div className="flex items-center gap-2 text-gray-500">
                    <hr className="flex-1 border-gray-700"/>
                    <span>or</span>
                    <hr className="flex-1 border-gray-700"/>
                </div>

                {/* Sign Up Redirect */}
                <Link to="/signup_evdriver" className="block mt-2">
                    <button className="btn w-full bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-full">
                        Sign Up
                    </button>
                </Link>
            </div>
        </div>
    );
}
