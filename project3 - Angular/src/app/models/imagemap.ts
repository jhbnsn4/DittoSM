import { IPost } from "./post";
import { IUserAccount } from "./useraccount";

export interface IImageMap {
    imageId: number;
    imageFile: string | ArrayBuffer | null;
    postFK: IPost | null;
    profileFK: IUserAccount | null;
}