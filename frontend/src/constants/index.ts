export const PRODUCTOS_URL = 'http://localhost:8080/api/productos'
export const MARCAS_URL = 'http://localhost:8080/api/marcas'

export const PRODUCTO_INICIAL = {
  nombre: '',
  descripcion: '',
  precio: 0,
  eliminado: false,
  marca: {
    id: 0,
    nombre: '',
    descripcion: '',
    eliminado: false,
  },
  id: 0,
}

export const MARCA_INICIAL = {
  nombre: '',
  descripcion: '',
  eliminado: false,
}
