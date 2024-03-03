import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from '@angular/common/http';


export const loggingInterceptor: HttpInterceptorFn = (request: HttpRequest<unknown>, next:
  HttpHandlerFn) => {
  const token = localStorage.getItem('token');
  const refreshToken = localStorage.getItem('refreshToken');

  const authEndpoint = request.url.includes('auth/')

  if (!authEndpoint) {
    const authReq = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
        'Refresh-Token' : refreshToken || '',
      }
    });

    return next(authReq);

  } else {
    return next(request);
  }


};