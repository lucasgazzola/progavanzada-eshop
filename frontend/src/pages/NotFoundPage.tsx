import { Link } from 'react-router-dom'

function NotFoundPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen w-full bg-gray-900 text-white">
      <h1 className="text-6xl font-bold mb-4">404</h1>
      <p className="text-2xl mb-8">Página no encontrada</p>
      <Link
        to="/"
        className="bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700">
        Volver al Inicio
      </Link>
    </div>
  )
}

export default NotFoundPage
