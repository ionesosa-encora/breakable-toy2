import React from 'react';

interface Track {
  id: string;
  name: string;
  duration_ms: number;
  albumImageUrl: string; 
}

interface TopTracksProps {
  tracks: Track[];
}

const formatDuration = (ms: number): string => {
  const minutes = Math.floor(ms / 60000);
  const seconds = Math.floor((ms % 60000) / 1000);
  return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
};

const TopTracks: React.FC<TopTracksProps> = ({ tracks }) => {
  return (
    <div className="top-tracks">
      <h2>Top Tracks</h2>
      <ul className="top-tracks__list">
        {tracks.map((track) => (
          <li key={track.id} className="top-tracks__item">
            {/* Miniatura del álbum */}
            <img 
              src={track.albumImageUrl || 'https://via.placeholder.com/64'} 
              alt={`Album cover for ${track.name}`} 
              className="top-tracks__album-image" 
            />
            {/* Información del track */}
            <div className="top-tracks__info">
              <h3 className="top-tracks__name">{track.name}</h3>
              <p className="top-tracks__duration">{formatDuration(track.duration_ms)}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TopTracks;
