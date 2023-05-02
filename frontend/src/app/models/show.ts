import { Content } from './content';

export interface Show extends Content {
  numberOfEpisodes: number;
  numberOfSeasons: number;
  lastYear: string;
}
