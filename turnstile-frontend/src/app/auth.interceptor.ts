//TODO: understand why kcloak is not including the token in the OPTIONS request, and why we getting cors error

// import { HttpInterceptorFn } from '@angular/common/http';

// export const authInterceptor: HttpInterceptorFn = (req, next) => {
//   const accessToken = localStorage.getItem('access_token');
//   if (accessToken) {
//     const cloned = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${accessToken}`
//       }
//     });

//     return next(cloned);
//   } else {
//     return next(req);
//   }
// };
