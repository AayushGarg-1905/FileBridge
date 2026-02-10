import { Avatar, Typography } from "@mui/material";
import { useUser } from "@clerk/clerk-react";
import { useLocation, useNavigate } from "react-router-dom";
import { sidebarLinks } from "../../data/sidebarData";

const Sidebar = () => {
  const { user } = useUser();
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="w-64 min-h-screen border-r bg-white px-4 py-6">
      {/* Profile */}
      <div className="flex flex-col items-center mb-8">
        <Avatar
          src={user?.imageUrl}
          className="w-16 h-16 mb-2"
        />
        <Typography className="font-medium">
          {user?.fullName}
        </Typography>
      </div>

      {/* Links */}
      <div className="flex flex-col gap-1">
        {sidebarLinks.map((link) => {
          const Icon = link.icon;
          const isActive = location.pathname === link.path;

          return (
            <div
              key={link.id}
              onClick={() => navigate(link.path)}
              className={`flex items-center gap-3 px-3 py-2 rounded-md cursor-pointer
                ${
                  isActive
                    ? "bg-blue-600 text-white"
                    : "text-gray-700 hover:bg-gray-100"
                }`}
            >
              <Icon className="w-5 h-5" />
              <span className="text-sm font-medium">
                {link.name}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Sidebar;
