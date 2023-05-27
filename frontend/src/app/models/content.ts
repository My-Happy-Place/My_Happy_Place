export interface Content {
  idTMDB: number;
  name: string;
  overview: string;
  posterPath: string;
  releaseDate: string;
  isFavorite: boolean;
  mediaType: "movie" | "tv";
}
