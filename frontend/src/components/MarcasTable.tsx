import { MARCAS_URL } from '../constants'
import { MarcaDTO } from '../dtos/MarcaDTO'
import ToastType from '../enums/ToastType'
import useAppContext from '../hooks/useAppContext'
import MarcaItem from './MarcaItem'

type Props = {
  marcasList: MarcaDTO[]
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>
  incluirEliminados: boolean
  setEditingId: React.Dispatch<React.SetStateAction<number | null>>
  setIsEditing: React.Dispatch<React.SetStateAction<boolean>>
  setMarcasList: React.Dispatch<React.SetStateAction<MarcaDTO[]>>
  setNuevaMarca: React.Dispatch<React.SetStateAction<Omit<MarcaDTO, 'id'>>>
}

function MarcasTable({
  marcasList,
  setMarcasList,
  setNuevaMarca,
  setIsEditing,
  setEditingId,
  incluirEliminados,
  setIsModalOpen,
}: Props) {
  const { showToast } = useAppContext()

  const handleEdit = (marca: MarcaDTO) => {
    setNuevaMarca({
      nombre: marca.nombre,
      descripcion: marca.descripcion || '',
      eliminado: marca.eliminado,
    })
    setIsEditing(true)
    setEditingId(marca.id)
    setIsModalOpen(true)
  }

  // Función para eliminar una marca
  const handleDelete = async (id: number) => {
    // Si ya está eliminada la recuperamos
    const response = await fetch(`${MARCAS_URL}/${id}`)
    const data = await response.json()

    const confirmDelete = window.confirm(`¿Estás seguro de que deseas ${
      data.eliminado ? 'recuperar' : 'eliminar'
    } esta marca?
    `)
    if (!confirmDelete) return

    if (data.eliminado) {
      await fetch(`${MARCAS_URL}/${id}`, {
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
        message: 'La marca fue recuperada correctamente',
        type: ToastType.SUCCESS,
      })

      // Actualizamos la lista de marcas
      setMarcasList(prev =>
        prev.map(marca =>
          marca.id === id ? { ...marca, eliminado: false } : marca
        )
      )
      return
    }

    try {
      const response = await fetch(`${MARCAS_URL}/${id}`, {
        method: 'DELETE',
      })

      if (!response.ok) {
        throw new Error('Error al eliminar la marca')
      }

      showToast({
        message: 'La marca fue eliminada correctamente',
        type: ToastType.SUCCESS,
      })

      // Actualizamos la lista de marcas
      if (incluirEliminados) {
        setMarcasList(prev =>
          prev.map(marca =>
            marca.id === id ? { ...marca, eliminado: true } : marca
          )
        )
        return
      }
      setMarcasList(prev => prev.filter(marca => marca.id !== id))
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
            Eliminado
          </th>
          <th scope="col" className="px-6 py-3">
            Acción
          </th>
        </tr>
      </thead>
      <tbody>
        {marcasList.map(marca => (
          <MarcaItem
            key={marca.id}
            {...marca}
            onEdit={() => handleEdit(marca)}
            onDelete={() => handleDelete(marca.id)}
          />
        ))}
      </tbody>
    </table>
  )
}

export default MarcasTable
