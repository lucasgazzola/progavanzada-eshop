import { createContext, useState, ReactNode, useEffect } from 'react'
import { MarcaDTO } from '../dtos/MarcaDTO'
import useAppContext from '../hooks/useAppContext'

interface MarcasContextProps {
  marcas: MarcaDTO[]
  nuevaMarca: Omit<MarcaDTO, 'id'>
  addMarca: (marca: MarcaDTO) => void
  marcasList: MarcaDTO[]
  setMarcasList: React.Dispatch<React.SetStateAction<MarcaDTO[]>>
  setNuevaMarca: React.Dispatch<React.SetStateAction<Omit<MarcaDTO, 'id'>>>
  setIsEditing: React.Dispatch<React.SetStateAction<boolean>>
  isEditing: boolean
  setEditingId: React.Dispatch<React.SetStateAction<number | null>>
  editingId: number | null
  setIncluirEliminados: React.Dispatch<React.SetStateAction<boolean>>
  incluirEliminados: boolean
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>
}

export const MarcasContext = createContext<MarcasContextProps | undefined>(
  undefined
)

export const MarcasProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const { isModalOpen, setIsModalOpen } = useAppContext()
  const [marcas, setMarcas] = useState<MarcaDTO[]>([])
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

  const addMarca = (marca: MarcaDTO) => {
    setMarcas([...marcas, marca])
  }

  return (
    <MarcasContext.Provider
      value={{
        marcas,
        addMarca,
        marcasList,
        setMarcasList,
        setNuevaMarca,
        setIsEditing,
        isEditing,
        setEditingId,
        editingId,
        setIncluirEliminados,
        incluirEliminados,
        setIsModalOpen,
        nuevaMarca,
      }}>
      {children}
    </MarcasContext.Provider>
  )
}
