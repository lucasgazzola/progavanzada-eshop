import { useEffect, useState } from 'react'

function Home() {
  const [productos, setProductos] = useState<
    Array<{ id: number; nombre: string; precio: number; imagen: string }>
  >([])
  useEffect(() => {
    document.title = 'MegaStore/Inicio'
    const fetchProductos = async () => {
      const mockProductos = [
        {
          id: 1,
          nombre: 'Smartphone Pro X',
          precio: 899.99,
          imagen: 'https://via.placeholder.com/150',
        },
        {
          id: 2,
          nombre: 'Auriculares Bluetooth',
          precio: 129.99,
          imagen: 'https://via.placeholder.com/150',
        },
        {
          id: 3,
          nombre: 'Laptop Gamer',
          precio: 1299.99,
          imagen: 'https://via.placeholder.com/150',
        },
        {
          id: 4,
          nombre: 'Smart TV 4K',
          precio: 799.99,
          imagen: 'https://via.placeholder.com/150',
        },
      ]
      setProductos(mockProductos)
    }

    fetchProductos()
  }, [])
  return (
    <div className="container flex flex-col min-w-full min-h-screen py-10">
      <h1 className="text-4xl font-bold text-center text-blue-50">
        Bienvenido a MegaStore
      </h1>
      <p className="text-lg text-center text-gray-100 mb-10">
        Explora nuestra amplia variedad de productos y encuentra las mejores
        ofertas.
      </p>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 w-full px-10">
        {productos.map(producto => (
          <div
            key={producto.id}
            className="border flex flex-col max-w-[350px] w-full border-gray-300 shadow-lg rounded-lg overflow-hidden">
            <img
              src={producto.imagen}
              alt={producto.nombre}
              className="w-full h-40 object-cover"
            />
            <div className="p-4">
              <h2 className="text-xl font-semibold text-gray-50">
                {producto.nombre}
              </h2>
              <p className="text-lg text-green-600 font-bold">
                ${producto.precio.toFixed(2)}
              </p>
            </div>
            <div className="mt-auto p-4">
              <button className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700">
                Agregar al Carrito
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default Home
