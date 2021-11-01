import { IImageMap } from "./imagemap";
import { IPost } from "./post";

export interface IUserAccountPackaged {
    userId: number;
    firstName: string;
    lastName: string;
    birthday: string;
    statusText: string;
    profilePicture: string;
}