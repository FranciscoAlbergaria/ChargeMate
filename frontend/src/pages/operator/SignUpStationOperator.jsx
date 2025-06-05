import React from 'react';
import Logo from '../../assets/Logo_ChargeMate.png';
import { Link } from 'react-router-dom';


export default function SignUpStationOperator() {
    return (
        <div className="min-h-screen bg-[#202025] text-white flex items-start justify-center p-4 pt-12">
            <div className="w-full max-w-md space-y-6">
                {/* Logo */}
                <img className="mx-auto h-16 w-auto mb-4" src={Logo} alt="ChargeMate Logo"/>

                {/* Título */}
                <div className="text-center space-y-1">
                    <h2 className="text-l font-semibold">Create an account as a Station Operator</h2>
                    <p className="text-sm text-gray-400">Provide company and personal credentials to get started</p>
                </div>

                {/* Formulário */}
                <form className="space-y-4">
                    <input
                        type="text"
                        placeholder="Company Name"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="text"
                        placeholder="Company VAT or ID"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="text"
                        placeholder="Full Name"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="email"
                        placeholder="email@company.com"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />
                    <input
                        type="password"
                        placeholder="Confirm Password"
                        className="input input-bordered w-full bg-gray-800 text-white rounded-full"
                    />

                    <button className="btn w-full bg-gradient-to-r from-cyan-400 to-blue-500 text-white rounded-full">
                        Continue
                    </button>
                </form>

                {/* Alternativa de navegação */}
                <div className="text-center text-sm text-gray-400 space-y-1">
                    <p>
                        Already have an account? <Link to="/signin" className="underline cursor-pointer">Sign In</Link>
                    </p>
                    <p>
                        Not a station operator? <Link to="/signup_evdriver" className="underline">Register as EV
                        Driver</Link>
                    </p>
                </div>

                {/* Termos */}
                <p className="text-xs text-center text-gray-500">
                    By registering, you agree to our{' '}
                    <span className="underline">Terms of Service</span> and{' '}
                    <span className="underline">Privacy Policy</span>.
                </p>
            </div>
        </div>
    );
}
