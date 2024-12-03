import { createContext, useState } from 'react'
import ToastType from '../enums/ToastType'
import Toast from '../components/Toast'

type AppContextType = {
  showToast: (toastMessage: ToastMessage) => void
  isModalOpen: boolean
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>
}

type ToastMessage = {
  message: string
  type: ToastType
}

export const AppContext = createContext<AppContextType>({
  showToast: () => {},
  isModalOpen: false,
  setIsModalOpen: () => {},
})

export const AppContextProvider = ({ children }: React.PropsWithChildren) => {
  const [toast, setToast] = useState<ToastMessage | null>(null)
  const [isModalOpen, setIsModalOpen] = useState(false)

  return (
    <AppContext.Provider
      value={{
        showToast: toastMessage => {
          setToast(toastMessage)
        },
        isModalOpen,
        setIsModalOpen,
      }}>
      {toast && (
        <Toast
          message={toast.message}
          type={toast.type}
          onClose={() => {
            setToast(null)
          }}
        />
      )}
      {children}
    </AppContext.Provider>
  )
}
