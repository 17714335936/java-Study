//using {z.sap.com.cap.odata as my} from '../db/schema';
//
//@path:'admin2'
//service AdminService {
//    entity Libros                  as projection on my.Libros;
//    entity Clientes                as projection on my.Clientes;
//
//    entity ClientesCamposFiltrados as projection on Clientes {
//        *
//    } excluding {
//        DNI,
//        contrasenia
//    };
//
//
//    entity AutoresCamposFiltrados  as projection on Autores {
//        *
//    } excluding {
//        fechaNacimiento,
//        ventaDirecta
//    };
//
//    entity Autores                 as projection on my.Autores;
//    entity Editoriales             as projection on my.Editoriales;
//    entity Editoriales_autores     as projection on my.Editoriales_autores;
//    entity Clientes_libros         as projection on my.Clientes_libros;
//
//    entity ResumenLibro            as
//        select
//            nombre                                 as nombreCliente,
//            librosComprados.libro.autor.nombre     as nombreEditorial,
//            librosComprados.libro.editorial.nombre as nombreAutor,
//            librosComprados.libro.nombre           as nombreLibro
//        from Clientes;
//
//
//}
