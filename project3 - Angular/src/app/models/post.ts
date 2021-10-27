import { IImageMap } from "./images";
import { IUser } from "./user";

export interface IPost{
  postId: number;
  text: string;
  numLikes: number;
  createdTime: Date;
  authorFK: IUser;
  imageList: IImageMap[];
  likes: IUser[];
}