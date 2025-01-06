import React from 'react';

interface Track {
  id: string;
  name: string;
  duration_ms: number;
  trackNumber: number;
}

interface TrackListProps {
  tracks: Track[];
}

const formatDuration = (ms: number): string => {
  const minutes = Math.floor(ms / 60000);
  const seconds = Math.floor((ms % 60000) / 1000);
  return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
};

const TrackList: React.FC<TrackListProps> = ({ tracks }) => {
  return (
    <div className="track-list">
      <h2>Album Songs</h2>
      <table className="track-list__table">
        <thead>
          <tr>
            <th>#</th>
            <th>Song Name</th>
            <th>Song Length</th>
          </tr>
        </thead>
        <tbody>
          {tracks.map((track) => (
            <tr key={track.id}>
              <td>{track.trackNumber}</td>
              <td>{track.name}</td>
              <td>{formatDuration(track.duration_ms)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TrackList;
