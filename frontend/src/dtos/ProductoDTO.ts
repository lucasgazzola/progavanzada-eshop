import { MarcaDTO } from './MarcaDTO'

export type ProductoDTO = {
  id: number
  nombre: string
  descripcion: string
  precio: number
  marca: MarcaDTO
}
