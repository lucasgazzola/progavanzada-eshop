import { useEffect } from 'react'

function Home() {
  useEffect(() => {
    document.title = 'MegaStore/Inicio'
  }, [])
  return (
    <div className="flex justify-center items-center mt-10">
      <div className="text-center p-6 shadow-lg rounded-lg">
        <h1 className="text-4xl font-bold mb-4 text-blue-100">
          ¡Bienvenido a MegaStore!
        </h1>
        <p className="text-lg text-gray-50">
          Explora nuestra amplia variedad de productos y encuentra las mejores
          ofertas.
        </p>
      </div>
    </div>
  )
}

export default Home
