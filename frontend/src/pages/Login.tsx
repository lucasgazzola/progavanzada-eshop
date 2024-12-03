import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault()
    // Aquí puedes agregar la lógica de autenticación

    console.log('Username:', username)
    console.log('Password:', password)
    navigate('/')
  }

  return (
    <div className="container flex flex-col min-w-full min-h-screen py-10 items-center justify-center">
      <form
        onSubmit={handleLogin}
        className="p-8 bg-[#232323] shadow-md shadow-gre w-full max-w-md ">
        <div className="mb-4">
          <h1 className="text-4xl font-copperplate font-helveticaCompressed text-center text-blue-50 mb-6">
            Iniciar Sesión
          </h1>
          <label
            htmlFor="username"
            className="block text-gray-100 text-sm font-bold mb-2">
            Correo Electrónico
          </label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={e => setUsername(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="mb-6">
          <label
            htmlFor="password"
            className="block text-gray-100 text-sm font-bold mb-2 ">
            Contraseña
          </label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="flex items-center justify-between">
          <button
            type="submit"
            className="bg-[#205988] text-white py-2 px-4 font-semibold  hover:bg-[#2d86c2] active:bg-[#184766]">
            Iniciar Sesión
          </button>
        </div>
      </form>
    </div>
  )
}

export default Login
