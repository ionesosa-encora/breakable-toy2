import React from 'react';
import { useNavigate } from 'react-router-dom';

interface ArtistCardProps {
  id: string;
  name: string;
  imageUrl: string | null;
  followers: number;
  popularity: number;
  genres: string[];
}

const ArtistCard: React.FC<ArtistCardProps> = ({ id, name, imageUrl, followers, popularity, genres }) => {
  const navigate = useNavigate();

  // Mostrar solo los primeros 2 géneros
  const displayedGenres = genres.slice(0, 2).join(', ');

  const handleCardClick = () => {
    // Navegar a la página del artista
    navigate(`/artists/${id}`);
  };

  return (
    <div 
      key={id} 
      className="card clickable-card" 
      onClick={handleCardClick} 
    >
      <img src={imageUrl || 'https://via.placeholder.com/150'} alt={name} className="card-image" />
      <h3 className="card-title">{name}</h3>
      <p className="card-text">Followers: {followers.toLocaleString()}</p>
      <p className="card-text">Popularity: {popularity}</p>
      {displayedGenres && <p className="card-text">Genres: {displayedGenres}</p>}
    </div>
  );
};

export default ArtistCard;
