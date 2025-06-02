import React from 'react';
import Logo from '../assets/Logo_ChargeMate.png'; // Adjust the path as necessary
import { Link } from 'react-router-dom'; // Import Link for navigation

export default function SignUpEVdriver() {
    return (
        <div className="min-h-screen bg-#202025 text-white flex items-center justify-center p-4">
            <div className="w-full max-w-md space-y-6">
                {/* Logo / App Name */}
               <img className={"mx-auto h-16 w-auto mb-4"} src={Logo} alt="ChargeMate Logo" />

                {/* Title + Subtitle */}
                <div className="text-center space-y-1">
                    <h2 className="text-xl font-semibold">Create an account as an EV Driver</h2>
                    <p className="text-sm text-gray-400">Enter the following details to get started</p>
                </div>

                {/* Form */}
                <form className="space-y-4">
                    <input
                        type="email"
                        placeholder="email@domain.com"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="text"
                        placeholder="first and last names"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="password"
                        placeholder="define password"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="password"
                        placeholder="confirm password"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />

                    <button className="btn w-full bg-gradient-to-r from-cyan-400 to-blue-500 text-white rounded-full">
                        Continue
                    </button>
                </form>

                {/* Divider */}
                <div className="flex items-center gap-2 text-gray-500">
                    <hr className="flex-1 border-gray-700"/>
                    <span>or</span>
                    <hr className="flex-1 border-gray-700"/>
                </div>

                {/* Station Operator Button */}
                <Link to="/signup_stationoperator" className="block mt-3">
                    <button className="btn w-full bg-white text-gray-900 rounded-full">
                        Create an account as a Station Operator
                    </button>
                </Link>


                {/* Sign In */}
                <button className="btn w-full bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-full">
                    Sign In
                </button>

                {/* Terms */}
                <p className="text-xs text-center text-gray-500">
                    By clicking continue, you agree to our{' '}
                    <span className="underline">Terms of Service</span> and{' '}
                    <span className="underline">Privacy Policy</span>.
                </p>
            </div>
        </div>
    );
}
