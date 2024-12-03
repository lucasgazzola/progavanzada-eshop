import { MARCAS_URL } from '../constants'

export const deleteMarca = async (id: number) => {
  const response = await fetch(`${MARCAS_URL}/${id}`)
  const data = await response.json()

  const confirmDelete = window.confirm(`¿Estás seguro de que deseas ${
    data.eliminado ? 'recuperar' : 'eliminar'
  } esta marca?
    `)
  if (!confirmDelete) return
}
