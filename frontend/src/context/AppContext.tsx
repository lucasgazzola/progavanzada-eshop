import { createContext, useState } from 'react'
import ToastType from '../enums/ToastType'
import Toast from '../components/Toast'

type AppContextType = {
  showToast: (toastMessage: ToastMessage) => void
}

type ToastMessage = {
  message: string
  type: ToastType
}

export const AppContext = createContext<AppContextType>({
  showToast: () => {},
})

export const AppContextProvider = ({ children }: React.PropsWithChildren) => {
  const [toast, setToast] = useState<ToastMessage | null>(null)
  return (
    <AppContext.Provider
      value={{
        showToast: toastMessage => {
          setToast(toastMessage)
        },
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
