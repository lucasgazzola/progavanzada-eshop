import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()

    try {
      const response = await fetch('http://localhost:8080/api/auth/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
        credentials: 'include',
      })

      console.log({ response })
      const data = await response.json()
      console.log({ data })

      // if (!response.ok) {
      // }
    } catch (error) {
      console.error('Error: ', error)
    }
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
        <div className="mt-2">
          <span>
            No tienes cuenta?{' '}
            <Link className="text-blue-300 hover:underline" to={'/register'}>
              Registrate
            </Link>
          </span>
        </div>
      </form>
    </div>
  )
}

export default Login
