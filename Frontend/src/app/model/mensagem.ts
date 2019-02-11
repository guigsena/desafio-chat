import { Usuario } from './usuario';

export class Mensagem {
    conteudo: string;
    de: Usuario;
    para: Usuario;
    dataEnvio: Date;
    dataLeitura: Date;
}
