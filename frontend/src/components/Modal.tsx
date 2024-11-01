import React from 'react'
import { MARCA_INICIAL, MARCAS_URL } from '../constants'
import { MarcaDTO } from '../dtos/MarcaDTO'
import ToastType from '../enums/ToastType'
import useAppContext from '../hooks/useAppContext'

type Props = {
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>
  isEditing: boolean
  editingId: number | null
  setEditingId: React.Dispatch<React.SetStateAction<number | null>>
  setIsEditing: React.Dispatch<React.SetStateAction<boolean>>
  nuevaMarca: Omit<MarcaDTO, 'id'>
  setNuevaMarca: React.Dispatch<React.SetStateAction<Omit<MarcaDTO, 'id'>>>
}

function Modal({
  setIsModalOpen,
  isEditing,
  editingId,
  setIsEditing,
  setEditingId,
  nuevaMarca,
  setNuevaMarca,
}: Props) {
  const { showToast } = useAppContext()

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setNuevaMarca({
      ...nuevaMarca,
      [e.target.name]: e.target.value,
    })
  }

  // Función para agregar o editar una marca
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (nuevaMarca.nombre.trim() === '') {
      showToast({
        message: 'El nombre no puede estar vacío',
        type: ToastType.ERROR,
      })
      return
    }

    try {
      const url = editingId
        ? `${MARCAS_URL}/${editingId}` // Si estamos editando, actualizamos la marca
        : `${MARCAS_URL}` // Si estamos agregando, creamos una nueva

      const method = editingId ? 'PUT' : 'POST'

      const response = await fetch(url, {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nombre: nuevaMarca.nombre.trim(),
          descripcion: nuevaMarca.descripcion.trim(),
          eliminado: nuevaMarca.eliminado,
        }),
      })

      if (!response.ok) {
        const data = await response.json()
        throw new Error(data.details[0])
      }

      showToast({
        message: editingId
          ? 'La marca se actualizó correctamente'
          : 'La marca se agregó correctamente',
        type: ToastType.SUCCESS,
      })

      setNuevaMarca({ ...MARCA_INICIAL })
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

  return (
    <div
      onClick={() => setIsModalOpen(false)}
      className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center">
      <div
        onClick={e => e.stopPropagation()}
        className="dark:bg-gray-900 antialiased rounded-lg shadow-lg p-6 w-full max-w-md">
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-bold">
            {isEditing ? 'Editar Marca' : 'Agregar Marca'}
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
              value={nuevaMarca.descripcion || ''}
              rows={5}
            />
          </div>
          <div className="mt-4 flex items-center gap-4">
            <label className="block text-gray-50">Eliminado</label>
            <input
              type="checkbox"
              className="px-2 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-500"
              placeholder="Descripción de la marca"
              onChange={() =>
                setNuevaMarca({
                  ...nuevaMarca,
                  eliminado: !nuevaMarca.eliminado,
                })
              }
              name="descripcion"
              checked={nuevaMarca.eliminado}
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
  )
}

export default Modal
