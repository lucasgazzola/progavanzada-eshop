import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

function Register() {
  const navigate = useNavigate()

  const [nombre, setNombre] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault()
    // Aquí puedes agregar la lógica de registro
    if (!nombre || !email || !password || !confirmPassword) {
      alert('Por favor llena todos los campos')
      return
    }
    if (password !== confirmPassword) {
      alert('Las contraseñas no coinciden')
      return
    }

    try {
      const response = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nombre, email, password, role: 'USER' }),
      })

      if (!response.ok) {
        const dataError = (await response.json()) as {
          details: string[]
          error: string
          path: string
          status: number
          timestamp: string
        }
        const errorDetails = dataError.details.join(', ')
        console.error('Error: ', errorDetails)
        alert('Ocurrió un error: ' + errorDetails)
        throw new Error(dataError.details.join(', '))
      }
      alert('Usuario registrado correctamente')
      navigate('/login')
    } catch (error) {
      console.error('Error: ', error)
    }
  }

  return (
    <div className="container flex flex-col min-w-full min-h-screen py-10 items-center justify-center">
      <form
        onSubmit={handleRegister}
        className="p-8 bg-[#232323] shadow-md shadow-gre w-full max-w-md ">
        <div className="mb-4">
          <h1 className="text-4xl font-copperplate font-helveticaCompressed text-center text-blue-50 mb-6">
            Registrarse
          </h1>
          <label
            htmlFor="name"
            className="block text-gray-100 text-sm font-bold mb-2">
            Nombre
          </label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            value={nombre}
            onChange={e => setNombre(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="mb-4">
          <label
            htmlFor="email"
            className="block text-gray-100 text-sm font-bold mb-2">
            Correo Electrónico
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="mb-4">
          <label
            htmlFor="password"
            className="block text-gray-100 text-sm font-bold mb-2">
            Contraseña
          </label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="mb-6">
          <label
            htmlFor="confirmPassword"
            className="block text-gray-100 text-sm font-bold mb-2">
            Confirmar Contraseña
          </label>
          <input
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            onChange={e => setConfirmPassword(e.target.value)}
            className="w-full px-3 py-2 border text-gray-900 bg-slate-50"
            required
          />
        </div>
        <div className="flex items-center justify-between">
          <button
            type="submit"
            className="bg-[#205988] text-white py-2 px-4 font-semibold  hover:bg-[#2d86c2] active:bg-[#184766]">
            Registrarse
          </button>
        </div>
        <div className="mt-2">
          <span>
            Ya tienes cuenta?{' '}
            <Link className="text-blue-300 hover:underline" to={'/login'}>
              Inicia Sesión
            </Link>
          </span>
        </div>
      </form>
    </div>
  )
}

export default Register
