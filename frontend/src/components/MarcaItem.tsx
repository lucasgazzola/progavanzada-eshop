type Props = {
  nombre: string
  descripcion: string
}

function MarcaItem({ nombre, descripcion }: Props) {
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
      <td className="px-4 py-3 flex items-center justify-end">
        <button
          className="inline-flex items-center text-sm font-medium hover:bg-gray-100 dark:hover:bg-gray-700 p-1.5 dark:hover-bg-gray-800 text-center text-gray-500 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
          type="button">
          <svg
            className="w-5 h-5"
            aria-hidden="true"
            fill="currentColor"
            viewBox="0 0 20 20"
            xmlns="http://www.w3.org/2000/svg">
            <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z" />
          </svg>
        </button>
      </td>
    </tr>
  )
}

export default MarcaItem
