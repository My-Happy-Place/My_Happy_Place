import { Content } from './content';
import { Season } from './season';

export interface Show extends Content {
  numberOfEpisodes: number;
  numberOfSeasons: number;
  lastYear: string;
  seasons: Season[];
}
