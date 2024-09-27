import { MarcaDTO } from '../dtos/MarcaDTO'

type Props = {
  id: number
  nombre: string
  descripcion: string
  precio: number
  marca: MarcaDTO
  onEdit: () => void
  onDelete: () => Promise<void>
}

function ProductoItem({
  nombre,
  descripcion,
  precio,
  marca,
  onEdit,
  onDelete,
}: Props) {
  return (
    <tr className="border-b dark:border-gray-700">
      <th
        scope="row"
        className="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white">
        {nombre}
      </th>
      <td title={descripcion} className="px-4 py-3 max-w-[12rem] truncate">
        {descripcion}
      </td>
      <td className="px-4 py-3 max-w-[12rem] truncate">{precio}</td>
      <td className="px-4 py-3 max-w-[12rem] truncate">{marca.nombre}</td>
      <td className="px-4 py-3 flex items-center justify-end gap-2">
        <button
          className="inline-flex items-center text-sm font-medium text-blue-600 dark:text-blue-500 hover:underline"
          onClick={onEdit}>
          Editar
        </button>
        <button
          className="inline-flex items-center text-sm font-medium text-red-600 dark:text-red-500 hover:underline"
          onClick={onDelete}>
          Eliminar
        </button>
      </td>
    </tr>
  )
}

export default ProductoItem
