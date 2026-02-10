import { UserButton } from "@clerk/clerk-react";
import { Wallet } from "lucide-react";
import { IconButton, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Topbar = () => {
  const navigate = useNavigate();
//   const {user} = useUser();

  return (
    <div className="flex items-center justify-between px-6 py-3 border-b bg-white">
      {/* Left */}
      <div className="flex items-center gap-2">
        <div className="w-8 h-8 bg-blue-600 rounded-md" />
        <Typography className="font-semibold text-lg">
          FileBridge
        </Typography>
      </div>

      {/* Right */}
      <div className="flex items-center gap-3">
        <IconButton onClick={() => navigate("/subscription")}>
          <Wallet />
          <span>5 credits</span>
        </IconButton>
        <UserButton />
      </div>
    </div>
  );
};

export default Topbar;
