import React, { useEffect, useState } from "react";

function MusicList() {
  const [musics, setMusics] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/musics")
      .then((res) => res.json())
      .then((data) => setMusics(data));
  }, []);

  return (
    <div>
      <h2>Lista de Músicas</h2>
      <ul>
        {musics.map((music) => (
          <li key={music.id}>
            {music.title} - {music.artist} ({music.album})
          </li>
        ))}
      </ul>
    </div>
  );
}

export default MusicList;
