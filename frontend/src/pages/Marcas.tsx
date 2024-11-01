import { useEffect, useState } from 'react'
import { MarcaDTO } from '../dtos/MarcaDTO'
import { MARCA_INICIAL, MARCAS_URL } from '../constants'
import Modal from '../components/Modal'
import MarcasTable from '../components/MarcasTable'

function Marcas() {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [nuevaMarca, setNuevaMarca] = useState<Omit<MarcaDTO, 'id'>>({
    nombre: '',
    descripcion: '',
    eliminado: false,
  })
  const [marcasList, setMarcasList] = useState<Array<MarcaDTO>>([])
  const [incluirEliminados, setIncluirEliminados] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState<number | null>(null) // Almacena el ID cuando estamos editando

  useEffect(() => {
    document.title = 'MegaStore/Marcas'
  }, [])

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
    const fetchMarcas = async () => {
      const response = await fetch(
        `${MARCAS_URL}?incluirEliminados=${incluirEliminados}`
      )
      const data = await response.json()
      setMarcasList(data)
    }
    fetchMarcas()
  }, [isModalOpen, incluirEliminados])

  return (
    <>
      <section className="bg-gray-50 dark:bg-gray-900 p-8 antialiased flex-1 relative">
        <div className="max-w-2xl">
          <div className="flex items-center gap-10 justify-between">
            <h2 className="text-xl font-bold dark:text-white">Marcas</h2>
            <div className="flex gap-2 items-center">
              <label htmlFor="mostarEliminados">Mostar eliminados</label>
              <input
                type="checkbox"
                name="mostarEliminados"
                onChange={() => setIncluirEliminados(!incluirEliminados)}
                checked={incluirEliminados}
              />
            </div>
            <button
              onClick={() => {
                setIsModalOpen(true)
                setIsEditing(false) // Nos aseguramos de que no estamos en modo edición
                setNuevaMarca({ ...MARCA_INICIAL })
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
              Agregar Marca
            </button>
          </div>
          <div className="mt-5">
            <div className="overflow-x-auto ">
              <MarcasTable
                marcasList={marcasList}
                incluirEliminados={incluirEliminados}
                setEditingId={setEditingId}
                setIsEditing={setIsEditing}
                setIsModalOpen={setIsModalOpen}
                setMarcasList={setMarcasList}
                setNuevaMarca={setNuevaMarca}
              />
            </div>
          </div>
        </div>
        {/* Modal para agregar o editar marca */}
        {isModalOpen && (
          <Modal
            isEditing={isEditing}
            nuevaMarca={nuevaMarca}
            editingId={editingId}
            setIsEditing={setIsEditing}
            setEditingId={setEditingId}
            setNuevaMarca={setNuevaMarca}
            setIsModalOpen={setIsModalOpen}
          />
        )}
      </section>
    </>
  )
}

export default Marcas
