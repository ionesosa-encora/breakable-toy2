import React from "react";

interface Track {
  id: string;
  name: string;
  duration_ms: number;
  albumImageUrl: string; // Imagen del álbum
  albumName: string; // Nombre del álbum
  artistName: string; // Nombre del artista
}

interface TrackSearchProps {
  tracks: Track[];
}

const formatDuration = (ms: number): string => {
  const minutes = Math.floor(ms / 60000);
  const seconds = Math.floor((ms % 60000) / 1000);
  return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
};

const TrackSearch: React.FC<TrackSearchProps> = ({ tracks }) => {
  return (
    <div className="track-search">
      <h2>Tracks</h2>
      <ul className="track-search__list">
        {tracks.map((track) => (
          <li key={track.id} className="track-search__item">
            {/* Imagen del álbum */}
            <img
              src={track.albumImageUrl || "https://via.placeholder.com/64"}
              alt={`Album cover for ${track.name}`}
              className="track-search__album-image"
            />
            {/* Información del track */}
            <div className="track-search__info">
              <h3 className="track-search__name">{track.name}</h3>
              <p className="track-search__artist">{track.artistName}</p>
              <p className="track-search__album">{track.albumName}</p>
              <p className="track-search__duration">{formatDuration(track.duration_ms)}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TrackSearch;
