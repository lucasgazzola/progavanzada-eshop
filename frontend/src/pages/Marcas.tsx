import { useEffect, useState } from 'react'
import MarcaItem from '../components/MarcaItem'
import { MarcaDTO } from '../dtos/MarcaDTO'
import useAppContext from '../hooks/useAppContext'
import ToastType from '../enums/ToastType'

function Marcas() {
  const { showToast } = useAppContext()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [nuevaMarca, setNuevaMarca] = useState({
    nombre: '',
    descripcion: '',
  })
  const [marcasList, setMarcasList] = useState<Array<MarcaDTO>>([])

  useEffect(() => {
    const fetchMarcas = async () => {
      const response = await fetch('http://localhost:8080/api/marcas')
      const data = await response.json()
      setMarcasList(data)
      console.log(data)
    }
    fetchMarcas()
  }, [isModalOpen])

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setNuevaMarca({
      ...nuevaMarca,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    if (nuevaMarca.nombre === '' || nuevaMarca.nombre.trim() === '') {
      showToast({
        message: 'El nombre no puede estar vacío',
        type: ToastType.ERROR,
      })
      return
    }

    try {
      const response = await fetch('http://localhost:8080/api/marcas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nombre: nuevaMarca.nombre.trim(),
          descripcion: nuevaMarca.descripcion.trim(),
        }),
      })

      const data = (await response.json()) as {
        path: string
        status: number
        timestamp: string
        error: string
        details: Array<string>
      }

      if (data.status === 409) {
        throw new Error(data.details[0])
      }

      showToast({
        message: 'La marca se agregó correctamente',
        type: ToastType.SUCCESS,
      })

      setNuevaMarca({
        nombre: '',
        descripcion: '',
      })

      setIsModalOpen(prev => !prev)
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
        <div className="max-w-2xl">
          <div className="flex items-center justify-between">
            <h2 className="text-xl font-bold dark:text-white">Marcas</h2>
            <button
              onClick={() => setIsModalOpen(prev => !prev)}
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
              Agregar
            </button>
          </div>
          <div className="mt-5">
            <div className="overflow-x-auto ">
              <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>
                    <th scope="col" className="px-6 py-3">
                      Nombre
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Descripcion
                    </th>
                    <th scope="col" className="px-6 py-3">
                      Acción
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {marcasList.map(marca => (
                    <MarcaItem key={marca.id} {...marca} />
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
                <h2 className="text-xl font-bold">Agregar Marca</h2>
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
                    placeholder="Nombre de la marca"
                    onChange={handleInputChange}
                    name="nombre"
                    value={nuevaMarca.nombre}
                    required
                    autoFocus
                  />
                </div>
                <div className="mt-4">
                  <label className="block text-gray-50">Descripción</label>
                  <textarea
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500"
                    placeholder="Descripción de la marca"
                    onChange={handleInputChange}
                    name="descripcion"
                    value={nuevaMarca.descripcion}
                    rows={5}
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
                    Agregar
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

export default Marcas
