import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'
import { ToastProvider } from 'react-toast-notifications'

ReactDOM.render(
  <React.StrictMode>
    <ToastProvider placement='top-center' autoDismiss={true}>
      <App />
    </ToastProvider>
  </React.StrictMode>,
  document.getElementById('root')
)

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA