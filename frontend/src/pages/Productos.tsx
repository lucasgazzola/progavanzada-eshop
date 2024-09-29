import { useEffect, useState } from 'react'
import useAppContext from '../hooks/useAppContext'
import { ProductoDTO } from '../dtos/ProductoDTO'
import ToastType from '../enums/ToastType'
import ProductoItem from '../components/ProductoItem'
import { MarcaDTO } from '../dtos/MarcaDTO'
import { MARCAS_URL, PRODUCTO_INICIAL, PRODUCTOS_URL } from '../constants'

function Productos() {
  const { showToast } = useAppContext()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [nuevoProducto, setNuevoProducto] = useState<ProductoDTO>({
    ...PRODUCTO_INICIAL,
  })
  const [productosList, setProductosList] = useState<Array<ProductoDTO>>([])
  const [marcasList, setMarcasList] = useState<Array<MarcaDTO>>([])
  const [incluirEliminados, setincluirEliminados] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState<number | null>(null) // Almacena el ID cuando estamos editando

  useEffect(() => {
    const handleEscape = (event: KeyboardEvent) => {
      if (event.key === 'Escape' && isModalOpen) {
        setIsModalOpen(false)
        setIsEditing(false)
      }
    }

    document.addEventListener('keydown', handleEscape)

    // Limpieza del evento al desmontar el componente
    return () => {
      document.removeEventListener('keydown', handleEscape)
    }
  }, [isModalOpen])

  useEffect(() => {
    const fetchProductos = async () => {
      const response = await fetch(
        `${PRODUCTOS_URL}?incluirEliminados=${incluirEliminados}`
      )
      const data = await response.json()
      setProductosList(data)
    }
    const fetchMarcas = async () => {
      const response = await fetch(MARCAS_URL)
      const data = await response.json()
      setMarcasList(data)
    }
    fetchMarcas()
    fetchProductos()
  }, [isModalOpen, incluirEliminados])

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setNuevoProducto({
      ...nuevoProducto,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (nuevoProducto.nombre.trim() === '') {
      showToast({
        message: 'El nombre no puede estar vacío',
        type: ToastType.ERROR,
      })
      return
    }

    try {
      const URL = editingId ? `${PRODUCTOS_URL}/${editingId}` : PRODUCTOS_URL

      const method = editingId ? 'PUT' : 'POST'

      const response = await fetch(URL, {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nombre: nuevoProducto.nombre.trim(),
          descripcion: nuevoProducto.descripcion?.trim(),
          precio: nuevoProducto.precio,
          marca: nuevoProducto.marca,
          eliminado: nuevoProducto.eliminado,
        }),
      })

      if (!response.ok) {
        const data = await response.json()
        throw new Error(data.details[0])
      }

      showToast({
        message: editingId
          ? 'El producto se actualizó correctamente'
          : 'El producto se agregó correctamente',
        type: ToastType.SUCCESS,
      })

      setNuevoProducto({ ...PRODUCTO_INICIAL })
      setIsModalOpen(false)
      setIsEditing(false)
      setEditingId(null) // Reiniciamos el estado de edición
    } catch (error) {
      if (error instanceof Error) {
        showToast({
          message: error.message,
          type: ToastType.ERROR,
        })
      }
    }
  }
  const handleEdit = (producto: ProductoDTO) => {
    setNuevoProducto({ ...producto, marca: { ...producto.marca } })
    setIsEditing(true)
    setEditingId(producto.id)
    setIsModalOpen(true)
  }
  const handleDelete = async (id: number) => {
    const response = await fetch(`${PRODUCTOS_URL}/${id}`)
    const data = await response.json()

    const confirmDelete = window.confirm(`¿Estás seguro de que deseas ${
      data.eliminado ? 'recuperar' : 'eliminar'
    } este producto?
    `)
    if (!confirmDelete) return
    if (data.eliminado) {
      await fetch(`${PRODUCTOS_URL}/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          eliminado: false,
        }),
      })
      // const data = await response.json()

      showToast({
        message: 'El producto fue recuperado correctamente',
        type: ToastType.SUCCESS,
      })

      // Actualizamos la lista de marcas
      setProductosList(prev =>
        prev.map(producto =>
          producto.id === id ? { ...producto, eliminado: false } : producto
        )
      )
      return
    }

    try {
      const response = await fetch(`${PRODUCTOS_URL}/${id}`, {
        method: 'DELETE',
      })

      if (!response.ok) {
        throw new Error('Error al eliminar el producto')
      }

      showToast({
        message: 'El producto fue eliminado correctamente',
        type: ToastType.SUCCESS,
      })

      if (incluirEliminados) {
        setProductosList(prev =>
          prev.map(producto =>
            producto.id === id ? { ...producto, eliminado: true } : producto
          )
        )
        return
      }
      setProductosList(prev => prev.filter(producto => producto.id !== id))
    } catch (error) {
      if (error instanceof Error) {
        showToast({
          message: error.message,
          type: ToastType.ERROR,
        })
      }
    }
  }

  return (
    <>
      <section className="bg-gray-50 dark:bg-gray-900 p-8 antialiased flex-1 relative">
        <div className="max-w-5xl">
          <div className="flex items-center justify-between">
            <h2 className="text-xl font-bold dark:text-white">Productos</h2>
            <div className="flex gap-2 items-center">
              <label htmlFor="mostarEliminados">Mostar eliminados</label>
              <input
                type="checkbox"
                name="mostarEliminados"
                onChange={() => setincluirEliminados(!incluirEliminados)}
                checked={incluirEliminados}
              />
            </div>
            <button
              onClick={() => {
                setIsModalOpen(true)
                setIsEditing(false) // Nos aseguramos de que no estamos en modo edición
                setNuevoProducto({ ...PRODUCTO_INICIAL })
              }}
              className="flex justify-center items-center bg-sky-700 hover:bg-blue-700 text-white text-sm font-bold py-2 px-3 rounded-md"
              type="button">
              <svg
                className="w-5 h-5"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
              </svg>
              Agregar Producto
            </button>
          </div>
          <div className="mt-5">
            <div className="overflow-x-auto ">
              <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                <thead className="text-xs text-center text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>
                    <th scope="col" className="px-6 py-3">
                      Nombre
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Descripción
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Precio
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Marca
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Eliminado
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Acción
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {productosList.map(producto => (
                    <ProductoItem
                      key={producto.id}
                      {...producto}
                      onEdit={() => handleEdit(producto)}
                      onDelete={() => handleDelete(producto.id)}
                    />
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        {isModalOpen && (
          <div
            onClick={() => setIsModalOpen(false)}
            className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center">
            <div
              onClick={e => e.stopPropagation()}
              className="dark:bg-gray-900 antialiased rounded-lg shadow-lg p-6 w-full max-w-md">
              <div className="flex justify-between items-center">
                <h2 className="text-xl font-bold">
                  {isEditing ? 'Editar Producto' : 'Agregar Producto'}
                </h2>
                <button
                  onClick={() => setIsModalOpen(false)} // Lógica para cerrar el modal
                  className="text-gray-500 hover:text-gray-700">
                  <svg
                    className="w-6 h-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg">
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M6 18L18 6M6 6l12 12"></path>
                  </svg>
                </button>
              </div>

              <form onSubmit={handleSubmit}>
                <div className="mt-4">
                  <label className="block text-gray-50">Nombre</label>
                  <input
                    type="text"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500"
                    placeholder="Nombre del producto"
                    onChange={handleInputChange}
                    name="nombre"
                    value={nuevoProducto.nombre}
                    required
                    autoFocus
                  />
                </div>
                <div className="mt-4">
                  <label className="block text-gray-50">Precio</label>
                  <input
                    type="number"
                    step="0.01"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500"
                    placeholder="Precio del producto"
                    onChange={handleInputChange}
                    name="precio"
                    value={nuevoProducto.precio}
                    required
                  />
                </div>
                <div className="mt-4">
                  <label className="block text-gray-50">Marca</label>
                  <select
                    value={nuevoProducto.marca?.id} // Asegúrate de que 'marca' esté definido
                    onChange={e => {
                      setNuevoProducto({
                        ...nuevoProducto,
                        marca: {
                          ...nuevoProducto.marca,
                          id: parseInt(e.target.value),
                        },
                      })
                    }}
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500">
                    {marcasList.map(marca => (
                      <option key={marca.id} value={marca.id}>
                        {marca.nombre}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mt-4">
                  <label className="block text-gray-50">Descripción</label>
                  <textarea
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500"
                    placeholder="Descripción del producto"
                    onChange={handleInputChange}
                    name="descripcion"
                    value={nuevoProducto.descripcion || ''}
                    rows={5}
                  />
                </div>
                <div className="mt-4">
                  <label className="block text-gray-50">Eliminado</label>
                  <input
                    type="checkbox"
                    name="eliminado"
                    onChange={() => {
                      setNuevoProducto({
                        ...nuevoProducto,
                        marca: { ...nuevoProducto.marca },
                        eliminado: !nuevoProducto.eliminado,
                      })
                    }}
                    checked={nuevoProducto.eliminado}
                  />
                </div>

                <div className="mt-6 flex justify-end">
                  <button
                    type="button"
                    onClick={() => setIsModalOpen(false)}
                    className="bg-red-500 text-white px-4 py-2 rounded-lg mr-2 hover:bg-red-900">
                    Cancelar
                  </button>
                  <button
                    type="submit"
                    className="bg-sky-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                    {isEditing ? 'Actualizar' : 'Agregar'}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </section>
    </>
  )
}

export default Productos
