import React from 'react';
import { useNavigate } from 'react-router-dom';

interface AlbumCardProps {
  id: string;
  name: string;
  imageUrl: string;
  releaseDate: string;
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('en-US', { day: '2-digit', month: 'long', year: 'numeric' }).format(date);
};

const AlbumCard: React.FC<AlbumCardProps> = ({ id, name, imageUrl, releaseDate }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/album/${id}`);
  };

  return (
    <div 
      key={id} 
      className="album-card clickable-card"
      onClick={handleCardClick}
    >
      <img src={imageUrl || 'https://via.placeholder.com/150'} alt={name} className="album-card__image" />
      <h3 className="album-card__name">{name}</h3>
      <p className="album-card__release-date">{formatDate(releaseDate)}</p>
    </div>
  );
};

export default AlbumCard;
