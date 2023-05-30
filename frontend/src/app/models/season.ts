import { Episode } from './episode';

export interface Season {
  idTMDB: number;
  seasonNumber: number;
  name: string;
  overview: string;
  posterPath: string;
  episodes: Episode[];
}
